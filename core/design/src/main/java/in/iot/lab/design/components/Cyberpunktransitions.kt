package `in`.iot.lab.design.transitions

import androidx.compose.animation.*
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween

/**
 * Simple, clean transitions — fade + subtle scale.
 * Noticeable but never distracting.
 */
object CyberpunkTransitions {

    private val easeOut = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)

    // ── Default: fade + gentle scale up (enter) / scale down + fade (exit) ──
    val defaultEnter: EnterTransition =
        fadeIn(tween(280, easing = easeOut)) +
                scaleIn(tween(280, easing = easeOut), initialScale = 0.94f)

    val defaultExit: ExitTransition =
        fadeOut(tween(200)) +
                scaleOut(tween(200), targetScale = 0.97f)

    val defaultPopEnter: EnterTransition =
        fadeIn(tween(280, easing = easeOut)) +
                scaleIn(tween(280, easing = easeOut), initialScale = 0.97f)

    val defaultPopExit: ExitTransition =
        fadeOut(tween(200)) +
                scaleOut(tween(200), targetScale = 0.94f)

    // ── Faculty detail: more pronounced scale — card expanding to fill screen ──
    val facultyDetailEnter: EnterTransition =
        fadeIn(tween(320, easing = easeOut)) +
                scaleIn(tween(320, easing = easeOut), initialScale = 0.88f)

    val facultyDetailExit: ExitTransition =
        fadeOut(tween(220)) +
                scaleOut(tween(220), targetScale = 0.95f)

    val facultyDetailPopEnter: EnterTransition =
        fadeIn(tween(280, easing = easeOut)) +
                scaleIn(tween(280, easing = easeOut), initialScale = 0.95f)

    val facultyDetailPopExit: ExitTransition =
        fadeOut(tween(220)) +
                scaleOut(tween(220), targetScale = 0.88f)

    // ── Post review sheet: slides up from bottom ──
    val sheetEnter: EnterTransition =
        fadeIn(tween(300, easing = easeOut)) +
                slideInVertically(tween(300, easing = easeOut)) { it / 5 }

    val sheetExit: ExitTransition =
        fadeOut(tween(220)) +
                slideOutVertically(tween(220)) { it / 5 }

    // ── Tab switching: crossfade only, no movement ──
    val tabEnter: EnterTransition  = fadeIn(tween(220))
    val tabExit: ExitTransition    = fadeOut(tween(180))
}