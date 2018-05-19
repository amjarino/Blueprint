package jahirfiquitiva.libs.blueprint.ui.widgets

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.robertlevonyan.views.chip.Chip
import com.robertlevonyan.views.chip.OnChipClickListener
import jahirfiquitiva.libs.kauextensions.extensions.cardBackgroundColor
import jahirfiquitiva.libs.kauextensions.extensions.dividerColor

class FilterChip : Chip {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr)
    
    var filterSelected = false
        set(value) {
            field = value
            changeBackgroundColor(
                    if (value) {
                        Color.parseColor("#ff00ff")
                    } else {
                        Color.parseColor("#ffffff")
                    }
                                 )
            requestLayout()
        }
    
    init {
        strokeColor = context.dividerColor
        backgroundColor = context.cardBackgroundColor
    }
    
    override fun setChipText(chipText: String?) {
        super.setChipText(chipText)
        requestLayout()
    }
    
    override fun setOnChipClickListener(onChipClickListener: OnChipClickListener?) {
        super.setOnChipClickListener {
            filterSelected = !filterSelected
            onChipClickListener?.onChipClick(it)
        }
    }
    
    fun setOnFilterSelectedListener(onSelected: (Boolean) -> Unit) {
        setOnChipClickListener {
            onSelected(filterSelected)
        }
    }
}