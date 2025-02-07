/*
 * Copyright 2023 The Android Open Source Project
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

import Foundation
import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all, edges: .top.union(.horizontal)).ignoresSafeArea(.keyboard)
    }
}

struct NestedContentView: View {
    let index: Int

    var body: some View {
        Text("Hello from SwiftUI #\(index)")
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        SwiftHelper().getViewController { index in
            let viewController = UIHostingController(rootView: NestedContentView(index: Int(index)))
            return viewController
        }
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
