package autoRefetching

import basic.PostData
import kotlinext.js.jso
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.INPUT
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import react.Props
import react.dom.*
import react.fc
import react.query.useMutation
import react.query.useQuery
import react.query.useQueryClient
import react.useState
import server_url
import styled.css
import styled.styledSpan
import wrappers.AxiosResponse
import wrappers.QueryError
import wrappers.axios
import wrappers.fetch
import kotlin.js.Promise

private const val example_url = "$server_url/ar"

typealias Todos = Array<String>

fun cAutoRefetching() = fc("AutoRefetching") { _: Props ->
    val queryClient = useQueryClient()
    val (intervalMs, setIntervalMs) = useState(1000)
    val (value, setValue) = useState("")
    val query = useQuery<Any, QueryError, AxiosResponse<Todos>, Any>(
        "todos",
        {
            axios<PostData>(
                jso { url = example_url }
            )
        },
        jso {
            refetchInterval = intervalMs
        }
    )
    val addMutation = useMutation<Any, Any, Any, Any>(
        { _value: String ->
            fetch("$example_url/add/$_value")
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries("todos")
            }
        }
    )
    val clearMutation = useMutation<Any, Any, Any, Any>(
        {
            fetch("$example_url/clear")
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries("todos")
            }
        }
    )
    if (query.isLoading) div { +"Loading .." }
    else if (query.isError) div { +query.error?.message!! }
    else {
        div {
            h1 {
                +"Auto Refetch with stale-time set to 1s"
            }
            p {
                +"""This example is best experienced on your own machine, where you can open
                    multiple tabs to the same localhost server and see your changes
                    propagate between the two."""
            }
            label {
                +"Query Interval speed (ms): "
                input {
                    attrs.value = intervalMs.toString()
                    attrs.type = InputType.number
                    attrs.step = "100"
                    attrs.onChangeFunction = {
                        val node = it.target.unsafeCast<INPUT>()
                        setIntervalMs(node.value.toInt())
                    }
                }
                styledSpan {
                    css {
                        display = Display.inlineBlock
                        marginLeft = 5.rem
                        width = 10.px
                        height = 10.px
                        if(query.isFetching) {
                            backgroundColor = Color.green
                            transition("all", 3.s, Timing.ease)
                        } else {
                            backgroundColor = Color.transparent
                        }
                        borderRadius = LinearDimension("100%")
                        transform = Transforms().also { it.scale(2) }
                    }
                }
            }
            h2 { +"Todo List" }
            form {
                attrs.onSubmit = {
                    it.preventDefault()
                    addMutation.mutate(
                        value,
                        jso {
                            onSuccess = { _: Any, _: Any, _: Any ->
                                Promise { resolve, _ ->
                                    setValue(" ")
                                    resolve(1)
                                }
                            }
                        }
                    )
                }
                input {
                    attrs.placeholder = "enter something"
                    attrs.value = value
                    attrs.onChangeFunction = {
                        val node = it.target.unsafeCast<INPUT>()
                        setValue(node.value)
                    }
                }
            }
            ul {
                query.data?.data?.map {
                    li {
                        +it
                        attrs.key = it
                    }
                }
            }
            div {
                button {
                    +"Clear all"
                    attrs.onClickFunction = {
                        clearMutation.mutate(0, null)
                    }
                }
            }
        }
    }
}

