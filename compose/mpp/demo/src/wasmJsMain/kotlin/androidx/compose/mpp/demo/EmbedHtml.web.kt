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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement

@Composable
actual fun Modifier.addHtmlElementWithCompose(id: String): Modifier {
    val currentId = remember { id }

    DisposableEffect(currentId) {
        val div = document.createElement("div") as HTMLDivElement
        div.id = currentId
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
            println("onDispose")
            div.remove()
        }
    }

    return this then Modifier.onGloballyPositioned { coordinates ->
        val position = coordinates.positionInRoot()
        val existingDiv = document.getElementById(currentId) as HTMLDivElement
        existingDiv.style.apply {
            left = "${position.x}px"
            top = "${position.y}px"
        }
    }
}
