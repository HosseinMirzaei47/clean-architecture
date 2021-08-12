package comtest.dandeliontest.todotest.ui.utils

import com.partsoftware.formmanager.exception.ExceptionMessageParams
import com.partsoftware.formmanager.exception.MessageParamTypes
import com.partsoftware.formmanager.exception.ValidationException
import com.partsoftware.formmanager.rules.base.ValidationSimpleRule
import comtest.dandeliontest.todotest.ui.R

class LengthLessThan(
    private val length: Int,
    private val stringParam: Int? = null
) : ValidationSimpleRule() {

    override fun validate(value: String?) {
        if (!value.isNullOrBlank() && value.length > length) {
            val param = stringParam?.let {
                ExceptionMessageParams(MessageParamTypes.STRING, it)
            } ?: ExceptionMessageParams(MessageParamTypes.HINT, null)
            throw ValidationException(R.string.task_limit, arrayOf(param))
        }
    }
}