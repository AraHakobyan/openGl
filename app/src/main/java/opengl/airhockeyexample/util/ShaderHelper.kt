package opengl.airhockeyexample.util

import android.opengl.GLES20.*
import android.util.Log

/**
 * Created by Aro-PC on 3/21/2018.
 */
class ShaderHelper{

    companion object {
        private final val TAG:String = "ShaderHelper"
        public fun compileVertexShader(shaderCode:String) : Int {
            return compileShader(GL_VERTEX_SHADER, shaderCode)
        }
        public fun compileFragmentShader(shaderCode:String) : Int {
            return compileShader(GL_FRAGMENT_SHADER, shaderCode)
        }
        private fun compileShader(type:Int, shaderCode: String) :Int {
            val shaderObjectId:Int = glCreateShader(type)
            if (shaderObjectId == 0){
                if (LoggerConfig.ON){
                    Log.w(TAG,"Couldn't create new shader")
                }
                return 0
            }
            glShaderSource(shaderObjectId,shaderCode)
            glCompileShader(shaderObjectId)
            val compileStatus= intArrayOf(1)
            glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus,0)
            if (LoggerConfig.ON){
                Log.v(TAG, "Resoultr of compile source: " + "\n" + shaderCode + "\n" + glGetShaderInfoLog(shaderObjectId))
            }

            if (compileStatus[0] == 0){
                glDeleteShader(shaderObjectId)
                if (LoggerConfig.ON){
                    Log.w(TAG,"Compilation of shader failed.")
                }
                return 0
            }
            return shaderObjectId
        }

        fun linkProgram(vertexShaderId:Int, fragmentShaderId:Int):Int{
            val programObjectId:Int = glCreateProgram()
            if (programObjectId == 0){
                if(LoggerConfig.ON){
                    Log.w(TAG, "Couldn't create new program")
                }
                return 0
            }
            glAttachShader(programObjectId,vertexShaderId)
            glAttachShader(programObjectId,fragmentShaderId)
            glLinkProgram(programObjectId)
            val linkStatus = intArrayOf(1)
            glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus,0)
            if (LoggerConfig.ON){
                Log.v(TAG, "Result of linking program:\n" + glGetProgramInfoLog(programObjectId))
            }

            if (linkStatus[0] == 0){
                glDeleteProgram(programObjectId)
                if (LoggerConfig.ON){
                    Log.w(TAG,"Linking of program failed.")
                }
                return 0
            }
            return programObjectId
        }

        fun validateProgram(programObjectId:Int) : Boolean {
            glValidateProgram(programObjectId)
            val validateStatus = intArrayOf(1)
            glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus,0)
            Log.v(TAG, "Result of validating program: " + validateStatus[0] + "\nLog: " + glGetProgramInfoLog(programObjectId))
            return validateStatus[0] != 0
        }
    }
}