package br.com.micdev.fid2

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class UserModel
{
     @SerializedName("cpf")
     @Expose
     open var cpf: String? = null

     @SerializedName("email")
     @Expose
     open var email: String? = null

     @SerializedName("name")
     @Expose
     open var name: String? = null

     @SerializedName("password")
     @Expose
     open var password: String? = null

     @SerializedName("dataNasc")
     @Expose
     open var dataNasc: String? = null


     override fun toString(): String {
         return "UserModel{"+
                 "cpf='"+cpf+"'"+
                 ", email='"+email+"'"+
                 ", name='"+name+"'"+
                 ", password='"+password+"'"+
                 ", dataNasc='"+dataNasc +
                 "}"

     }
}