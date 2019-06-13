package br.com.micdev.fid2.qrcode

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import android.view.View
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.content.Context.DOWNLOAD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.app.DownloadManager
import android.net.Uri
import br.com.micdev.fid2.R


class QRCodeUtils {
    companion object{
        private const val QRcodeWidth = 800

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
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
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

                val uri = Uri.parse(f.path)

                /*val request = DownloadManager.Request(uri)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, eventId)
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // to notify when download is complete
                request.allowScanningByMediaScanner()// if you want to be available from media players
                val manager = getSystemService(view.context,DownloadManager::class.java) as DownloadManager*/
                //manager.enqueue(request)
                return f.absolutePath
            } catch (e1: IOException) {
                e1.printStackTrace()
            }

            return ""

        }

    }
}