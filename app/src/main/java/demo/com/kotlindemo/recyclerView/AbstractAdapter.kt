package demo.com.kotlindemo.recyclerView

import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class AbstractAdapter<ITEM> constructor(
        protected var items: MutableList<ITEM>,
        private val choiceMode : ChoiceMode,
        private val layoutResId: Int)
    : RecyclerView.Adapter<AbstractAdapter.Holder>() {

    val selectedItemViewCount: Int
        get() = selectedItemViews.size()

    private val selectedItemViews = SparseBooleanArray()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.itemView.bind(item)
    }

    protected open fun View.bind(item: ITEM) {
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addItems(items: MutableList<ITEM>, position: Int) {
        addItemsInternal(items, position)
        notifyItemRangeInserted(position, items.size)
    }

    fun addItem(item: ITEM, position: Int) {
        addItemInternal(item, position)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        removeItemInternal(position)
        notifyItemRemoved(position)
    }

    fun clearItems() {
        clearItemsInternal()
        clearSelectedItemViews()
    }

    fun isItemViewToggled(position: Int): Boolean = selectedItemViews.get(position, false)

    fun getSelectedItemViews(): MutableList<Int> {
        val items: MutableList<Int> = arrayListOf()
        (0 until selectedItemViews.size()).mapTo(items) { selectedItemViews.keyAt(it) }
        return items
    }

    fun getSelectedItems() : MutableList<ITEM>{
        val items : MutableList<ITEM> = ArrayList()
        for (it in getSelectedItemViews()){
             items.add(this.items[it])
        }
        return items
    }

    fun clearSelectedItemViews() {
        selectedItemViews.clear()
        notifyDataSetChanged()
    }

    open fun toggleItemView(position: Int) {
        when (choiceMode) {
            ChoiceMode.NONE -> return
            ChoiceMode.SINGLE -> {
                getSelectedItemViews().forEach {
                    selectedItemViews.delete(it)
                    notifyItemChanged(it)
                }
                selectedItemViews.put(position, true)
            }

            ChoiceMode.MULTIPLE -> if (isItemViewToggled(position)) {
                selectedItemViews.delete(position)
            } else {
                selectedItemViews.put(position, true)
            }
        }
        notifyItemChanged(position)
    }

    protected open fun addItemsInternal(items: MutableList<ITEM>, position: Int) {
        this.items.addAll(position, items)
    }

    protected open fun addItemInternal(item: ITEM, position: Int) {
        this.items.add(position, item)
    }

    protected open fun removeItemInternal(position: Int) {
        removeSelectedItemView(position)

        this.items.removeAt(position)
    }

    protected open fun clearItemsInternal() {
        this.items.clear()
    }

    private fun removeSelectedItemView(position: Int) {
        val selectedPositions = getSelectedItemViews()
        if (isItemViewToggled(position)) {
            selectedPositions.removeAt(selectedPositions.indexOf(position))
        }

        selectedItemViews.clear()
        for (selectedPosition in selectedPositions) {
            selectedItemViews.put(if (position > selectedPosition) selectedPosition else selectedPosition - 1, true)
        }
    }
}