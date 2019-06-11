package br.com.micdev.fid2.qrcode

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import android.view.View
import br.com.micdev.fid2.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class QRCodeUtils {
    companion object{
        val QRcodeWidth = 800
        private val IMAGE_DIRECTORY = "/fid/qrcodes"

        @Throws(WriterException::class)
        fun textToImageEncode(Value: String,context: Context): Bitmap? {
            val bitMatrix: BitMatrix
            try {
                bitMatrix = MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
                )

            } catch (Illegalargumentexception: IllegalArgumentException) {

                return null
            }

            val bitMatrixWidth = bitMatrix.width

            val bitMatrixHeight = bitMatrix.height

            val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

            for (y in 0 until bitMatrixHeight) {
                val offset = y * bitMatrixWidth

                for (x in 0 until bitMatrixWidth) {

                    pixels[offset + x] = if (bitMatrix.get(x, y))
                        context.getColor(R.color.colorPrimary)
                    else
                        Color.WHITE
                }
            }
            val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)

            bitmap.setPixels(pixels, 0, 800, 0, 0, bitMatrixWidth, bitMatrixHeight)
            return bitmap
        }

        fun saveImage(view: View, myBitmap: Bitmap?,eventId:String): String {
            val bytes = ByteArrayOutputStream()
            myBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
            val wallpaperDirectory = File(
                Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY
            )
            // have the object build the directory structure, if needed.

            if (!wallpaperDirectory.exists()) {
                Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs())
                wallpaperDirectory.mkdirs()
            }

            try {
                val f = File(wallpaperDirectory, "$eventId.jpg")
                f.createNewFile()   //give read write permission
                val fo = FileOutputStream(f)
                fo.write(bytes.toByteArray())
                MediaScannerConnection.scanFile(view.context,
                    arrayOf(f.path),
                    arrayOf("image/jpeg"), null)
                fo.close()
                Log.d("TAG", "File Saved::--->" + f.absolutePath)

                return f.absolutePath
            } catch (e1: IOException) {
                e1.printStackTrace()
            }

            return ""

        }

    }
}