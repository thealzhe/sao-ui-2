package com.tencao.saoui.api.elements.animator.internal

/**
 * This is a copy from the library LibrarianLib
 * This code is covered under GNU Lesser General Public License v3.0
 */

import com.tencao.saomclib.utils.math.clamp
import com.tencao.saoui.api.elements.animator.LerperHandler
import com.tencao.saoui.api.elements.animator.registerLerper
import java.awt.Color

/**
 * TODO: Document file VecLerpers
 *
 * Created by TheCodeWarrior
 */
object ColorLerper {
    init {
        LerperHandler.registerLerper(Color::class.javaObjectType) { from, to, frac ->
            fun compute(from: Int, to: Int): Int = (to * frac + from * (1 - frac)).toInt().clamp(0, 255)

            Color(
                compute(from.red, to.red),
                compute(from.green, to.green),
                compute(from.blue, to.blue),
                compute(from.alpha, to.alpha)
            )
        }
    }
}
