package demo.com.kotlindemo.recyclerView

import android.view.View

class Kadapter<ITEM>(items: MutableList<ITEM>,
                     choiceMode: ChoiceMode,
                     layoutResId: Int,
                     private val bindHolder: View.(ITEM,Int,Kadapter<ITEM>) -> Unit)
    : AbstractAdapter<ITEM>(items,choiceMode, layoutResId) {

    private var itemClick: ((ITEM,Int,Kadapter<ITEM>) -> Unit)? = null

    constructor(items: MutableList<ITEM>,
                choiceMode: ChoiceMode,
                layoutResId: Int,
                bindHolder: View.(ITEM,Int,Kadapter<ITEM>) -> Unit,
                itemClick: ((ITEM, Int,Kadapter<ITEM>) -> Unit)? = null) : this(items,choiceMode, layoutResId, bindHolder) {
        this.itemClick = itemClick
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val itemView = holder.itemView
        itemView.bindHolder(items[position],position,this)

        itemView.setOnClickListener {
            itemClick?.invoke(items[position],position,this)
            toggleItemView(position)
        }
    }
}