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
package jahirfiquitiva.libs.blueprint.ui.adapters

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.ViewGroup
import ca.allanwang.kau.utils.inflate
import jahirfiquitiva.libs.blueprint.R
import jahirfiquitiva.libs.blueprint.data.models.Filter
import jahirfiquitiva.libs.blueprint.helpers.utils.BPLog
import jahirfiquitiva.libs.blueprint.ui.adapters.viewholders.FilterChipHolder

class FiltersAdapter(private val onClick: (Filter, Boolean) -> Unit) :
        ListAdapter<Filter, FilterChipHolder>(Filter.CALLBACK) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterChipHolder {
        return FilterChipHolder(parent.inflate(R.layout.item_filter_chip))
    }
    
    override fun onBindViewHolder(holder: FilterChipHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}