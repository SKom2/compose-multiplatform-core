/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.mpp.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import kotlinx.browser.document
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLDivElement

fun getCanvasCoordinates(): Pair<Double, Double>? {
    val canvasElement = document.querySelector("canvas") as? HTMLCanvasElement
    return canvasElement?.getBoundingClientRect()?.let {
        it.left to it.top
    }
}

@Composable
actual fun Modifier.addHtmlElementWithCompose(id: String): Modifier {
    val density = LocalDensity.current.density
    val canvasCoordinates = getCanvasCoordinates()

    DisposableEffect(id) {
        val div = document.createElement("div") as HTMLDivElement
        div.id = id
        div.style.apply {
            position = "absolute"
            backgroundColor = "red"
            color = "white"
            padding = "2px"
            borderRadius = "5px"
        }
        div.innerText = "Hello"
        document.body?.appendChild(div)

        onDispose {
            div.remove()
        }
    }

    return this then Modifier.onGloballyPositioned { coordinates ->
        val bounds = coordinates.boundsInRoot()
        val position = coordinates.positionInRoot()
        val existingDiv = document.getElementById(id) as HTMLDivElement
        val scaledX = position.x / density
        val scaledY = position.y / density

        if (canvasCoordinates != null) {
            val (canvasX, canvasY) = canvasCoordinates
            val adjustedX = canvasX + scaledX
            val adjustedY = canvasY + scaledY

            existingDiv.style.apply {
                left = "${adjustedX}px"
                top = "${adjustedY}px"
            }
        } else {
            existingDiv.style.apply {
                left = "${scaledX}px"
                top = "${scaledY}px"
            }
        }

        val topClip = maxOf((bounds.top.toDouble() - position.y) / density, 0.0)

        if (topClip > 0) {
            existingDiv.style.setProperty("clip-path", "inset(${topClip}px 0 0 0)")
        } else {
            existingDiv.style.removeProperty("clip-path")
        }
    }
}