package rest.model

data class ChangePswCredentials(val username: String, val oldPsw: String, val newPsw: String)