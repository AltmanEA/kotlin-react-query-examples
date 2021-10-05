package basic

import kotlinext.js.jso
import kotlinx.css.Color
import kotlinx.css.FontWeight
import kotlinx.css.color
import kotlinx.css.fontWeight
import kotlinx.html.js.onClickFunction
import react.Props
import react.StateSetter
import react.dom.*
import react.fc
import react.query.QueryFunction
import react.query.useQuery
import react.query.useQueryClient
import styled.css
import styled.styledA
import wrappers.QueryError
import wrappers.axios

interface PostsProps : Props {
    var setPostId: StateSetter<Int>
}

private interface Data{
    val body: String
    val id: Int
    val title: String
    val userId: Int
}

private interface QueryData{
    val data: Array<Data>
}

private val postsQueryFunction: QueryFunction<Any, Any> = {
    axios<Any>(
        jso { url = "https://jsonplaceholder.typicode.com/posts" }
    )
}

fun cPosts() = fc("PostS") { props: PostsProps ->
    val queryClient = useQueryClient()
    val query = useQuery<Any, QueryError, QueryData, Any>("posts", postsQueryFunction, )
    div {
        h1 { +"Posts" }
        if (query.isLoading) div { +"Loading .." }
        else if (query.isError) div { +query.error?.message!! }
        else {
            query.data?.data?.map {post ->
                p {
                    attrs.key = post.id.toString()
                    styledA("#") {
                        attrs.onClickFunction = { props.setPostId(post.id) }
                        css {
                            val t = queryClient
                                .getQueryData<QueryData>("post, ${post.id}")
                                t?.let {
                                    fontWeight = FontWeight.bold
                                    color = Color.green
                                }
                        }
                        +(post.title)
                    }
                }
            }
        }
        div {
            if(query.isFetching)
                +"Updating..."
            else
                +""
        }
    }
}

