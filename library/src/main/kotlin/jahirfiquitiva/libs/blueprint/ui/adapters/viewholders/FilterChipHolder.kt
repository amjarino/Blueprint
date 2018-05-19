package jahirfiquitiva.libs.blueprint.ui.adapters.viewholders

import android.animation.Animator
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ca.allanwang.kau.utils.dpToPx
import ca.allanwang.kau.utils.gone
import jahirfiquitiva.libs.blueprint.R
import jahirfiquitiva.libs.blueprint.data.models.Filter
import jahirfiquitiva.libs.blueprint.ui.widgets.ChipCard
import jahirfiquitiva.libs.kauextensions.extensions.bind
import jahirfiquitiva.libs.kauextensions.extensions.cardBackgroundColor
import jahirfiquitiva.libs.kauextensions.extensions.context
import jahirfiquitiva.libs.kauextensions.extensions.getPrimaryTextColorFor

class FilterChipHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // val chip: FilterChip? by itemView.bind(R.id.filter_chip)
    val card: ChipCard? by itemView.bind(R.id.filter_card)
    val check: AppCompatCheckBox? by itemView.bind(R.id.filter_checkbox)
    val textView: TextView? by itemView.bind(R.id.filter_title)
    
    fun bind(filter: Filter, onClick: (Filter, Boolean) -> Unit) {
        card?.alpha = 0F
        check?.gone()
        card?.radius = (card?.measuredHeight ?: 8.dpToPx) / 2.0F
        val bgColor = if (filter.selected) filter.color else context.cardBackgroundColor
        card?.setCardBackgroundColor(bgColor)
        textView?.text = filter.title
        textView?.setTextColor(context.getPrimaryTextColorFor(bgColor, 0.6F))
        check?.isSelected = filter.selected
        check?.setOnCheckedChangeListener { buttonView, isChecked ->
            onClick(filter, isChecked)
        }
        card?.setOnClickListener {
            check?.let {
                val wasSelected = it.isSelected
                it.isSelected = !wasSelected
                card?.animate()?.alpha(1F)?.setDuration(500)?.setListener(
                        object : Animator.AnimatorListener {
                            override fun onAnimationCancel(animation: Animator?) {}
                            override fun onAnimationEnd(animation: Animator?) {
                                onClick(filter, !wasSelected)
                            }
                            
                            override fun onAnimationRepeat(animation: Animator?) {}
                            override fun onAnimationStart(animation: Animator?) {
                            }
                        })?.start()
            }
        }
        card?.animate()?.alpha(1F)?.setDuration(500)?.start()
        /*
        val bgColor = if (filter.selected) filter.color else context.cardBackgroundColor
        chip?.chipText = filter.title
        chip?.changeBackgroundColor(bgColor)
        chip?.strokeColor = context.dividerColor
        chip?.textColor = context.getPrimaryTextColorFor(bgColor, 0.6F)
        chip?.setOnFilterSelectedListener {
            BPLog.d { "From holder: $filter" }
            onClick(filter, it)
        }
        */
    }
}