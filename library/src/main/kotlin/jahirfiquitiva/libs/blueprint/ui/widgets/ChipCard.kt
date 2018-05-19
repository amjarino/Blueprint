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
package jahirfiquitiva.libs.blueprint.ui.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet

class ChipCard : CardView {
    constructor(context: Context) : super(context) {
        radius = 0F
    }
    
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        radius = 0F
    }
    
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr) {
        radius = 0F
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        radius = heightMeasureSpec / 2.0F
    }
    
    override fun setRadius(radius: Float) {
        super.setRadius(height.toFloat() / 2.0F)
        requestLayout()
    }
}