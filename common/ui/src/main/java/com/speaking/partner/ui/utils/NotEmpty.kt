import com.partsoftware.formmanager.exception.ExceptionMessageParams
import com.partsoftware.formmanager.exception.MessageParamTypes
import com.partsoftware.formmanager.exception.ValidationException
import com.partsoftware.formmanager.rules.base.ValidationSimpleRule
import com.speaking.partner.ui.R

class NotEmpty(private val stringParam: Int? = null) : ValidationSimpleRule() {

    override fun validate(value: String?) {
        if (value.isNullOrBlank()) {
            val param = stringParam?.let {
                ExceptionMessageParams(MessageParamTypes.STRING, it)
            } ?: ExceptionMessageParams(MessageParamTypes.HINT, null)
            throw ValidationException(R.string.validation_rule_not_empty, arrayOf(param))
        }
    }
}