/*
 * Copyright (c) 2018. Jahir Fiquitiva
 *
 * Licensed under the CreativeCommons Attribution-ShareAlike
 * 4.0 International License. You may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *    http://creativecommons.org/licenses/by-sa/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jahirfiquitiva.libs.blueprint.ui.fragments.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import ca.allanwang.kau.utils.dpToPx
import ca.allanwang.kau.utils.gone
import ca.allanwang.kau.utils.postDelayed
import ca.allanwang.kau.utils.setPaddingTop
import ca.allanwang.kau.utils.visible
import jahirfiquitiva.libs.blueprint.R
import jahirfiquitiva.libs.blueprint.data.models.Filter
import jahirfiquitiva.libs.blueprint.ui.adapters.FiltersAdapter
import jahirfiquitiva.libs.kauextensions.extensions.ctxt
import jahirfiquitiva.libs.kauextensions.extensions.isInHorizontalMode

class FiltersBottomSheet : BottomSheetDialogFragment() {
    
    private var recyclerView: RecyclerView? = null
    private var progress: ProgressBar? = null
    private var behavior: BottomSheetBehavior<*>? = null
    private val filters = ArrayList<String>()
    private val activeFilters = ArrayList<String>()
    
    private val adapter: FiltersAdapter by lazy {
        FiltersAdapter { filter, checked ->
            updateFiltersWith(filter, checked)
        }
    }
    
    private val sheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            var correctAlpha = slideOffset + 1
            if (correctAlpha < 0) correctAlpha = 0.0F
            if (correctAlpha > 1) correctAlpha = 1.0F
            bottomSheet.alpha = correctAlpha
        }
        
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) dismiss()
        }
    }
    
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)
        
        val detailView = View.inflate(context, R.layout.info_dialog, null)
        
        progress = detailView?.findViewById(R.id.loading_view)
        progress?.visible()
        
        recyclerView = detailView?.findViewById(R.id.info_rv)
        recyclerView?.gone()
        recyclerView?.setPaddingTop(8.dpToPx)
        
        adapter.setHasStableIds(false)
        updateFilters()
        recyclerView?.adapter = adapter
        
        val layoutManager = GridLayoutManager(
                ctxt, if (ctxt.isInHorizontalMode) 4 else 3,
                GridLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager
        
        recyclerView?.itemAnimator = DefaultItemAnimator()
        
        dialog?.setContentView(detailView)
        
        val params = (detailView.parent as? View)?.layoutParams as? CoordinatorLayout.LayoutParams
        val parentBehavior = params?.behavior
        
        if (parentBehavior != null && parentBehavior is BottomSheetBehavior<*>) {
            behavior = parentBehavior
            behavior?.setBottomSheetCallback(sheetCallback)
        }
    }
    
    private fun updateFiltersWith(filter: Filter, checked: Boolean) {
        if (checked) {
            activeFilters.add(filter.title)
        } else {
            activeFilters.remove(filter.title)
        }
        updateFilters()
    }
    
    private fun updateFilters() {
        val actualFilters = ArrayList<Filter>()
        filters.forEach {
            actualFilters.add(
                    Filter(it, Color.parseColor("#4285f4"), activeFilters.contains(it)))
        }
        updateFilters(actualFilters)
    }
    
    private fun updateFilters(actualFilters: ArrayList<Filter>) {
        adapter.submitList(ArrayList())
        postDelayed(10) {
            adapter.submitList(actualFilters)
            progress?.gone()
            recyclerView?.visible()
        }
    }
    
    fun show(context: FragmentActivity) {
        show(context.supportFragmentManager, TAG)
    }
    
    override fun show(manager: FragmentManager?, tag: String?) {
        super.show(manager, tag)
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }
    
    fun animateHide() {
        behavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior?.state = BottomSheetBehavior.STATE_HIDDEN
    }
    
    companion object {
        private val TAG = "FiltersBottomSheet"
        
        fun build(
                filters: ArrayList<String>,
                activeFilters: ArrayList<String>
                 ): FiltersBottomSheet =
                FiltersBottomSheet().apply {
                    this.filters.clear()
                    this.filters.addAll(filters)
                    this.activeFilters.clear()
                    this.activeFilters.addAll(activeFilters)
                }
        
        fun show(
                context: FragmentActivity,
                filters: ArrayList<String>,
                activeFilters: ArrayList<String>
                ) =
                build(filters, activeFilters).show(context.supportFragmentManager, TAG)
    }
}