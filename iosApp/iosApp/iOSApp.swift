import SwiftUI
import shared

@main
struct KMPizzaApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
 
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
 
class AppDelegate: UIResponder, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization
        CommonModuleKt.doInitKoin()
        return true
    }
}

