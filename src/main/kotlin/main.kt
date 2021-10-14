import autoRefetching.cAutoRefetching
import basic.cBasic
import kotlinx.browser.document
import react.dom.render
import react.query.QueryClient
import react.query.QueryClientProvider
import simple.cSimple
import wrappers.cReactQueryDevtools

const val example = 2
val examples = listOf(
    ::cSimple,
    ::cBasic,
    ::cAutoRefetching
)

const val server_url = "http://127.0.0.1:8080"

val queryClient = QueryClient()

fun main() {
    render(document.getElementById("root")) {
        QueryClientProvider {
            attrs.client = queryClient
            child(examples[example]()) {}
            child(cReactQueryDevtools()) {}
        }
    }
}