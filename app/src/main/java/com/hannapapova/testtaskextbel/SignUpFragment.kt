package com.hannapapova.testtaskextbel

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = MaskedTextChangedListener("+375 [00] [000]-[00]-[00]", et_phone_number)
        et_phone_number.addTextChangedListener(listener)
        et_phone_number.onFocusChangeListener = listener

        et_password.doOnTextChanged { text, start, before, count ->
            input_layout_password.error =
                if (!isPasswordValid(text.toString())) "Password must contain at least 8 characters and at least 1 digit"
                else null
        }

        btn_sign_up.setOnClickListener {
            input_layout_email.error =
                if (!isEmailValid(et_email.text.toString())) "Incorrect E-mail" else null

            input_layout_phone_number.error =
                if (!isPhoneNumberValid(et_phone_number.text.toString())) "Incorrect phone number" else null

            input_layout_password_again.error =
                if (!arePasswordsMatching(
                        et_password.text.toString(),
                        et_password_again.text.toString()
                    )
                ) "Passwords aren't matching" else null
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.length == 17
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8 && password.contains(Regex("\\d")) && password.isNotBlank()
    }

    private fun arePasswordsMatching(password: String, passwordAgain: String): Boolean {
        return password == passwordAgain
    }
}