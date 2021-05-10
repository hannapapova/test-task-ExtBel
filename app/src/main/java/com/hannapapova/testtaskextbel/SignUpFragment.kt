package com.hannapapova.testtaskextbel

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        addMaskForEditText(et_phone_number, "+375 [00] [000]-[00]-[00]")
        //region Set TextWatchers
        setTextWatchers()
        //endregion

        btn_sign_up.setOnClickListener {
            input_layout_email.error =
                if (!isEmailValid(et_email)) getString(R.string.incorrect_email) else null

            input_layout_phone_number.error =
                if (!isPhoneNumberValid(et_phone_number)) getString(R.string.incorrect_phone) else null

            input_layout_password.error =
                if (!isPasswordValid(et_password)) getString(R.string.password_must_contain)
                else null

            input_layout_password_again.error =
                if (!arePasswordsMatching(et_password, et_password_again))
                    getString(R.string.passwords_aren_t_matching) else null

            if (noErrorsLocated()) {
                showShortToast(context, getString(R.string.successfully_signed_up))
            }
        }

        //region Tiny OnClickListeners
        chevron_left.setOnClickListener {
            showShortToast(context, getString(R.string.navigate_back))
        }

        btn_login_with_google.setOnClickListener {
            showShortToast(context, getString(R.string.login_w_google))
        }

        btn_login_with_facebook.setOnClickListener {
            showShortToast(context, getString(R.string.login_w_facebook))
        }

        btn_login_with_apple.setOnClickListener {
            showShortToast(context, getString(R.string.login_w_apple))
        }
        //endregion
    }

    private fun setTextWatchers() {
        et_password.doOnTextChanged { text, start, before, count ->
            input_layout_password.error =
                if (!isPasswordValid(et_password)) getString(R.string.password_must_contain) else null
        }

        et_email.setOnFocusChangeListener { v, hasFocus ->
            input_layout_email.error = null
        }

        et_phone_number.setOnFocusChangeListener { v, hasFocus ->
            input_layout_phone_number.error = null
        }

        et_password_again.setOnFocusChangeListener { v, hasFocus ->
            input_layout_password_again.error = null
        }
    }

    private fun noErrorsLocated() =
        input_layout_email.error == null && input_layout_phone_number.error == null &&
                input_layout_password.error == null && input_layout_password_again.error == null

    private fun addMaskForEditText(editText: EditText, mask: String) {
        val listener = MaskedTextChangedListener(mask, editText)
        editText.addTextChangedListener(listener)
        editText.onFocusChangeListener = listener
    }

    private fun isEmailValid(editTextEmail: EditText) =
        Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString().trim()).matches()

    private fun isPhoneNumberValid(editTextPhoneNumber: EditText) =
        editTextPhoneNumber.text.toString().length == 17

    private fun isPasswordValid(editTextPassword: EditText): Boolean {
        val password = editTextPassword.text.toString()
        return password.length >= 8 && password.contains(Regex("\\d")) && password.isNotEmpty()
    }

    private fun arePasswordsMatching(editTextPassword: EditText, editTextPasswordAgain: EditText) =
        editTextPassword.text.toString() == editTextPasswordAgain.text.toString()
}