package rest.model.to

data class ChangePswCredentialsIN(val username: String, val oldPsw: String, val newPsw: String)