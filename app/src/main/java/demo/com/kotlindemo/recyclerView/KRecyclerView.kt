package demo.com.kotlindemo.recyclerView

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

fun <ITEM> RecyclerView.setUp(items: MutableList<ITEM>,
                              choiceMode: ChoiceMode = ChoiceMode.NONE,
                              layoutResId: Int,
                              bindHolder: View.(ITEM, Int,Kadapter<ITEM>) -> Unit,
                              itemClick: ((ITEM, Int,Kadapter<ITEM>) -> Unit)? = null,
                              manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)): Kadapter<ITEM> {
    return Kadapter(items, choiceMode, layoutResId, {item, i, kadapter ->
        bindHolder(item,i,kadapter)
    },  {item, i, kadapter ->
        itemClick?.invoke(item, i, kadapter)
    }).apply {
        layoutManager = manager
        adapter = this
    }
}