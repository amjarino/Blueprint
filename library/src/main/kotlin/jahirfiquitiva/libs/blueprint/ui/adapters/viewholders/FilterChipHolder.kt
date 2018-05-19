package jahirfiquitiva.libs.blueprint.ui.adapters.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import jahirfiquitiva.libs.blueprint.R
import jahirfiquitiva.libs.blueprint.data.models.Filter
import jahirfiquitiva.libs.blueprint.helpers.utils.BPLog
import jahirfiquitiva.libs.blueprint.ui.widgets.FilterChip
import jahirfiquitiva.libs.kauextensions.extensions.bind
import jahirfiquitiva.libs.kauextensions.extensions.cardBackgroundColor
import jahirfiquitiva.libs.kauextensions.extensions.context
import jahirfiquitiva.libs.kauextensions.extensions.dividerColor
import jahirfiquitiva.libs.kauextensions.extensions.getPrimaryTextColorFor

class FilterChipHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val chip: FilterChip? by itemView.bind(R.id.filter_chip)
    // val check: AppCompatCheckBox? by itemView.bind(R.id.filter_checkbox)
    
    fun bind(filter: Filter, onClick: (Filter, Boolean) -> Unit) {
        /*
        check?.text = filter.title
        check?.isSelected = filter.selected
        check?.setOnCheckedChangeListener { buttonView, isChecked ->
            onClick(filter, isChecked)
        }
        */
        val bgColor = if (filter.selected) filter.color else context.cardBackgroundColor
        chip?.chipText = filter.title
        chip?.changeBackgroundColor(bgColor)
        chip?.strokeColor = context.dividerColor
        chip?.textColor = context.getPrimaryTextColorFor(bgColor, 0.6F)
        chip?.setOnFilterSelectedListener {
            BPLog.d { "From holder: $filter" }
            onClick(filter, it)
        }
    }
}