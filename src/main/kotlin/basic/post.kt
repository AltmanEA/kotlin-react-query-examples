package basic

import kotlinext.js.jso
import kotlinx.html.js.onClickFunction
import react.Props
import react.StateSetter
import react.dom.a
import react.dom.div
import react.dom.h1
import react.dom.p
import react.fc
import react.query.useQuery
import wrappers.AxiosResponse
import wrappers.QueryError
import wrappers.axios

interface PostProps : Props {
    var postId: Int?
    var setPostId: StateSetter<Int>
}

interface PostData{
    val title: String
    val body: String
}

fun cPost() = fc("Post") { props: PostProps ->
    val id = props.postId
    val _url = "https://jsonplaceholder.typicode.com/posts/$id"
    val query = useQuery<Any, QueryError, AxiosResponse<PostData>, Any>(
        "post, $id",
        {
            axios<PostData>(
                jso { url = _url }
            )
        }
    )
    div {
        div {
            a {
                attrs.onClickFunction = { props.setPostId(-1) }
                attrs.href = "#"
                +"Back"
            }
            if (query.isLoading) div { +"Loading .." }
            else if (query.isError) div { +(query.error?.message?:"Error!") }
            else {
                val data = query.data?.data!!
                h1 { +(data.title) }
                div {
                    p { +data.body }
                }
                div {
                    if(query.isFetching)
                        +"Updating..."
                    else
                        +""
                }
            }
        }
    }
}