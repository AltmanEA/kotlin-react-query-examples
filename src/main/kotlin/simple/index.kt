package simple

import react.Props
import react.dom.div
import react.dom.h1
import react.dom.p
import react.dom.strong
import react.fc
import react.query.useQuery
import wrappers.fetch

fun cSimple() = fc("Simple") { _: Props ->
    val query = useQuery<Any, Any, Any, Any>(
        "repoData",
        {
            fetch("https://api.github.com/repos/tannerlinsley/react-query")
                .then { it.json() }
        }
    )

    if (query.isLoading) div { +"Loading .." }
    else if (query.isError) div { +"Error!" }
    else {
        div {
            val data: dynamic = query.data
            h1 { +(data.name as String) }
            p { +(data.description as String) }
            strong { +"\uD83D\uDC40 ${data.subscribers_count} " }
            strong { +"✨ ${data.stargazers_count} " }
            strong { +"\uD83C\uDF74 ${data.stargazers_count} " }
            div {
                if(query.isFetching)
                    +"Updating..."
                else
                    +""
            }
        }
    }
}