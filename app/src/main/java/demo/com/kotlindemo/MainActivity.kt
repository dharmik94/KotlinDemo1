package demo.com.kotlindemo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.util.Log
import android.view.View
import demo.com.kotlindemo.recyclerView.setUp
import demo.com.kotlindemo.recyclerView.ChoiceMode
import demo.com.kotlindemo.recyclerView.Kadapter
import demo.com.kotlindemo.recyclerView.selection.Selection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_selection.view.*
import kotlinx.android.synthetic.main.layout_selection.view.*

class MainActivity : AppCompatActivity() {

    var adapter : Kadapter<*>? = null
    var bottoSheetSingleSelection : BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    fun singleSelection(view: View){
        val list: MutableList<Selection> = ArrayList()
        list.add(Selection("test1"))
        list.add(Selection("test2"))
        list.add(Selection("test3"))
        list.add(Selection("test4"))
        list.add(Selection("test5"))
        list.add(Selection("test6"))
        loadBottomSheet("Single Selection",list)

    }

    private fun loadBottomSheet(title : String,list: MutableList<Selection>){
        if (bottoSheetSingleSelection== null){
            bottoSheetSingleSelection = BottomSheetDialog(this)
            bottoSheetSingleSelection?.setCancelable(false);
            bottoSheetSingleSelection?.setCanceledOnTouchOutside(false);
         //   bottoSheetSingleSelection?.getBehavior().setPeekHeight(150);
            val dialogSheetView = layoutInflater.inflate(R.layout.layout_selection, null)
            bottoSheetSingleSelection?.setContentView(dialogSheetView)
            loadRecyclerView(dialogSheetView,list)

            dialogSheetView.txt_title.text = title
            dialogSheetView.img_close.setOnClickListener {
                bottoSheetSingleSelection?.dismiss()
            }
            dialogSheetView.txt_clear.setOnClickListener {
                clearAll()
                bottoSheetSingleSelection?.dismiss()
            }
            dialogSheetView.txt_apply.setOnClickListener {
                applyAll()
            }

            bottoSheetSingleSelection?.show()
        }else{
            bottoSheetSingleSelection?.show()
        }
    }

    private fun loadRecyclerView(view: View,list: MutableList<Selection>) {
        adapter = view.recyclerView.setUp(list, ChoiceMode.MULTIPLE, R.layout.item_selection, { item, i, kadapter ->
            txt_name.text = item.name
            if (kadapter.isItemViewToggled(i)) {
                txt_name.setTextColor(Color.GREEN)
            } else {
                txt_name.setTextColor(Color.BLACK)
            }
        }, { item, i, kadapter ->
            // Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show()
        })

    }

    fun clearAll(){
        adapter?.clearSelectedItemViews()
    }

    fun applyAll(){
        val data = adapter?.getSelectedItems()
        if (data?.size!! >0){
            bottoSheetSingleSelection?.dismiss()
        }
    }

}
