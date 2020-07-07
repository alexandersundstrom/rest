package rest.util

import org.passay.CharacterRule
import org.passay.EnglishCharacterData
import org.passay.PasswordGenerator

class PswGenerator() {
    companion object {
        fun temporaryPassword(): String {
            val lowerCaseRule = CharacterRule(EnglishCharacterData.LowerCase, 3)
            val upperCaseRule = CharacterRule(EnglishCharacterData.UpperCase, 3)
            val digitRule = CharacterRule(EnglishCharacterData.Digit, 3)
            val specialRule = CharacterRule(EnglishCharacterData.Special, 3)

            return PasswordGenerator().generatePassword(12, lowerCaseRule, upperCaseRule, digitRule, specialRule)

        }
    }
}