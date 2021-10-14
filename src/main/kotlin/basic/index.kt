package basic

import react.Props
import react.dom.ReactHTML.p
import react.dom.ReactHTML.strong
import react.fc
import react.useState

fun cBasic() = fc("Basic"){_: Props ->
    val (postId, setPostId) = useState(-1)
    p {
        +"""As you visit the posts below, you will notice them in a loading state
        the first time you load them . However, after you return to this list and
        click on any posts you have already visited again, you will see them
        load instantly and background refresh right before your eyes!{ " " }"""
        strong {
            +"""(You may need to throttle your network speed to simulate longer
                    loading sequences)"""
        }
    }
    if(postId>-1)
        child(cPost()){
            attrs.postId = postId
            attrs.setPostId = setPostId
        }
    else
        child(cPosts()){
            attrs.setPostId = setPostId
        }
}