package com.example.error_crash

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.channels.FileChannel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * @author tamsiree
 * @date 2016/1/24
 * 数据处理相关
 *
 *
 * ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐
 * └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘
 * ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐
 * │~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │
 * ├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤
 * │ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │
 * ├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │
 * │ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │               │ 4 │ 5 │ 6 │   │
 * ├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤
 * │ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │
 * ├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││
 * │ Ctrl│    │Alt │         Space         │ Alt│    │    │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│
 * └─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘
 */
class RxDataTool {
    companion object {

        /**
         * MB与Byte的倍数
         */
        const val MB = 1048576

        /**
         * GB与Byte的倍数
         */
        const val GB = 1073741824

        /**
         * 字节数转合适大小
         *
         * 保留3位小数
         *
         * @param byteNum 字节数
         * @return 1...1024 unit
         */
        @JvmStatic
        fun byte2FitSize(byteNum: Long): String {
            return if (byteNum < 0) {
                "shouldn't be less than zero!"
            } else if (byteNum < RxConstTool.KB) {
                String.format(Locale.getDefault(), "%.3fB", byteNum.toDouble())
            } else if (byteNum < MB) {
                String.format(Locale.getDefault(), "%.3fKB", byteNum.toDouble() / RxConstTool.KB)
            } else if (byteNum < GB) {
                String.format(Locale.getDefault(), "%.3fMB", byteNum.toDouble() / MB)
            } else {
                String.format(Locale.getDefault(), "%.3fGB", byteNum.toDouble() / GB)
            }
        }


        /**
         * MD5加密文件
         *
         * @param file 文件
         * @return 文件的16进制密文
         */
        @JvmStatic
        fun encryptMD5File2String(file: File): String {
            return if (encryptMD5File(file) != null) bytes2HexString(encryptMD5File(file)!!) else ""
        }

        private val HEX_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F')

        /**
         * byteArr转hexString
         *
         * 例如：
         * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
         *
         * @param bytes byte数组
         * @return 16进制大写字符串
         */
        @JvmStatic
        fun bytes2HexString(bytes: ByteArray): String {
            val ret = CharArray(bytes.size shl 1)
            var i = 0
            var j = 0
            while (i < bytes.size) {
                ret[j++] = HEX_DIGITS[bytes[i].toInt() ushr 4 and 0x0f]
                ret[j++] = HEX_DIGITS[bytes[i].toInt() and 0x0f]
                i++
            }
            return String(ret)
        }

        /**
         * MD5加密文件
         *
         * @param file 文件
         * @return 文件的MD5校验码
         */
        @JvmStatic
        fun encryptMD5File(file: File): ByteArray? {
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(file)
                val channel = fis.channel
                val buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
                val md = MessageDigest.getInstance("MD5")
                md.update(buffer)
                return md.digest()
            } catch (e: NoSuchAlgorithmException ) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                RxFileTool.closeIO(fis)
            }
            return null
        }
    }
}