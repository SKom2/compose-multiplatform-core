/*
 * Copyright 2021 The Android Open Source Project
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
package androidx.compose.ui.text

import org.jetbrains.skia.icu.CharDirection


/**
 * Get strong (R, L or AL) direction type.
 * See https://www.unicode.org/reports/tr9/
 */
internal actual fun CodePoint.strongDirectionType(): StrongDirectionType =
    when (CharDirection.of(this)) {
        CharDirection.LEFT_TO_RIGHT -> StrongDirectionType.Ltr

        CharDirection.RIGHT_TO_LEFT,
        CharDirection.RIGHT_TO_LEFT_ARABIC -> StrongDirectionType.Rtl

        else -> StrongDirectionType.None
    }

internal actual fun CodePoint.isNeutralDirection(): Boolean =
    when (CharDirection.of(this)) {
        CharDirection.OTHER_NEUTRAL,
        CharDirection.WHITE_SPACE_NEUTRAL,
        CharDirection.BOUNDARY_NEUTRAL -> true

        else -> false
    }
