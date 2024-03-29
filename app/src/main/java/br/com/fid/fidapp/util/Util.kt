package br.com.fid.fidapp.util

import android.content.Context
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class Util{
    companion object{

        val tenMinutesInMillis:Long = 600000L

        val thirtyMinutesInMillis:Long = 1800000L

        val oneHourInMillis:Long = 3600000L

        val onDayInMillis:Long = 2678400000L

        fun showSnackFeedback(message : String, isValid : Boolean, view : View, context : Context){
            val snackbar : Snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            val v : View = snackbar.view
            if (isValid)
                v.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
            else
                v.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))

            snackbar.show()
        }

        fun showToastFeedbach(message: String,duration: Int,context: Context){
            Toast.makeText(context,message,duration).show()
        }

        @Suppress("NAME_SHADOWING")
        fun formatDateTime(dateTimeToFormat: String):String{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val dateTimeToFormat = "$dateTimeToFormat.00Z"
                val date = Instant.parse(dateTimeToFormat)
                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    .withLocale(Locale.getDefault())
                    .withZone(ZoneId.of("GMT"))
                return formatter.format(date)
            }else{
                val dateTimeToFormat = dateTimeToFormat.replace("-","").replace("T","").trim()
                val ano = dateTimeToFormat.substring(0..3)
                val mes = dateTimeToFormat.substring(4..5)
                val dia = dateTimeToFormat.substring(6..7)
                val horario = dateTimeToFormat.substring(8..12)

                return "$dia/$mes/$ano - $horario"
            }
        }

        fun isEmail(email : String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun myValidateCPF(cpf : String) : Boolean{
            val cpfClean = cpf.replace(".", "").replace("-", "")

            //## check if size is eleven
            if (cpfClean.length != 11)
                return false

            if(verificaDigitosIguais(cpfClean))
                return false

            //## check if is number
            try {
                @SuppressWarnings
                val number  = cpfClean.toLong()
            }catch (e : Exception){
                return false
            }

            //continue
            val dvCurrent10 = cpfClean.substring(9,10).toInt()
            val dvCurrent11= cpfClean.substring(10,11).toInt()

            //the sum of the nine first digits determines the tenth digit
            val cpfNineFirst = IntArray(9)
            var i = 9
            while (i > 0 ) {
                cpfNineFirst[i-1] = cpfClean.substring(i-1, i).toInt()
                i--
            }
            //multiple the nine digits for your weights: 10,9..2
            val sumProductNine = IntArray(9)
            var weight = 10
            var position = 0
            while (weight >= 2){
                sumProductNine[position] = weight * cpfNineFirst[position]
                weight--
                position++
            }
            //Verify the nineth digit
            var dvForTenthDigit = sumProductNine.sum() % 11
            dvForTenthDigit = 11 - dvForTenthDigit //rule for tenth digit
            if(dvForTenthDigit > 9)
                dvForTenthDigit = 0
            if (dvForTenthDigit != dvCurrent10)
                return false

            //### verify tenth digit
            val cpfTenFirst = cpfNineFirst.copyOf(10)
            cpfTenFirst[9] = dvCurrent10
            //multiple the nine digits for your weights: 10,9..2
            val sumProductTen = IntArray(10)
            var w = 11
            var p = 0
            while (w >= 2){
                sumProductTen[p] = w * cpfTenFirst[p]
                w--
                p++
            }
            //Verify the nineth digit
            var dvForeleventhDigit = sumProductTen.sum() % 11
            dvForeleventhDigit = 11 - dvForeleventhDigit //rule for tenth digit
            if(dvForeleventhDigit > 9)
                dvForeleventhDigit = 0
            if (dvForeleventhDigit != dvCurrent11)
                return false

            return true
        }

        fun verificaDigitosIguais(cpf : String):Boolean{
            val a = cpf.toCharArray()

            val i=cpf.length
            var j=0
            val p=a.get(0)

            while(j<i){
                val c = a.get(j)
                if(p!=c)
                    return false
                j++
            }
            return true
        }
    }
}