import basic.cBasic
import kotlinx.browser.document
import react.dom.render
import react.query.QueryClient
import react.query.QueryClientProvider
import simple.cSimple
import wrappers.cReactQueryDevtools

const val example = 1
val examples = listOf(
    ::cSimple,
    ::cBasic
)

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