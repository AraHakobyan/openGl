package opengl.airhockeyexample

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import opengl.airhockeyexample.util.LoggerConfig
import opengl.airhockeyexample.util.ShaderHelper
import opengl.airhockeyexample.util.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by Aro-PC on 3/20/2018.
 */
class AirHockeyRenderer : GLSurfaceView.Renderer {
    companion object {
        private val U_COLOR = "u_Color"
        private val A_POSITION = "a_Position"
    }

    private var uColorLocation: Int = 0
    private var aPositionLocation: Int = 0
    private val POSITION_COMPONENT_COUNT: Int = 2
    private var vertexData: FloatBuffer? = null
    private val BYTES_PER_FLOAT = 4
    private val context: Context
    private var program: Int = 0

    constructor(context: Context) {
        this.context = context
        val tableVerticesWithTriangles = floatArrayOf(
                // Triangle 1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,

                // Triangle 2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,

                // Line 1
                -0.5f, 0f,
                0.5f, 0f,

                // Mallets
                0f, -0.25f,
                0f, 0.25f
        )
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexData?.put(tableVerticesWithTriangles)
    }

    override fun onDrawFrame(p0: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)
        glLineWidth(10.0f)

        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 0, 6)

        //drawing the dividing line
        glUniform4f(uColorLocation, 0.0f, 0.0f, 0.0f, 1.0f)
        glDrawArrays(GL_LINES, 6, 2)

        //draw the first mallet blue
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        glDrawArrays(GL_POINTS, 8, 1)

        //draw the second mallet red
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        glDrawArrays(GL_POINTS, 9, 1)

    }

    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        glViewport(0, 0, p1, p2)
    }

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        val vertexShaderSource: String = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader)
        val fragmentShaderSource: String = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader)

        val vertexShader: Int = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragmentShader: Int = ShaderHelper.compileFragmentShader(fragmentShaderSource)
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program)
        }
        glUseProgram(program)
        uColorLocation = glGetUniformLocation(program, U_COLOR)
        aPositionLocation = glGetAttribLocation(program, A_POSITION)

        vertexData?.position(0)
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData)
        glEnableVertexAttribArray(aPositionLocation)

    }

}