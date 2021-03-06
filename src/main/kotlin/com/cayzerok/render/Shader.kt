package com.cayzerok.render

import com.cayzerok.core.path
import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.nio.FloatBuffer


private var program:Int? = null
private var vertexShader:Int? = null
private var fragmentShader:Int? = null
class Shader(fileName: String){
    private val shaderName = fileName
    fun init() {
        program = glCreateProgram()
        vertexShader = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader!!, readFile(shaderName+"VS.glsl"))
        glCompileShader(vertexShader!!)
        if(glGetShaderi(vertexShader!!, GL_COMPILE_STATUS) != 1) {
            throw Exception(glGetShaderInfoLog(vertexShader!!))
        }

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentShader!!, readFile(shaderName+"FS.glsl"))
        glCompileShader(fragmentShader!!)
        if(glGetShaderi(fragmentShader!!, GL_COMPILE_STATUS) != 1) {
            throw Exception(glGetShaderInfoLog(fragmentShader!!))
        }

        glAttachShader(program!!, vertexShader!!)
        glAttachShader(program!!, fragmentShader!!)

        glBindAttribLocation(program!!,0,"vertices")
        glBindAttribLocation(program!!,1,"textures")

        glLinkProgram(program!!)
        if (glGetProgrami(program!!, GL_LINK_STATUS) != 1) {
            throw Exception(glGetProgramInfoLog(program!!))
        }
        glValidateProgram(program!!)

        if (glGetProgrami(program!!, GL_VALIDATE_STATUS) != 1) {
            throw Exception(glGetProgramInfoLog(program!!))
        }
    }

    fun setUniform(name:String, value:Float) {
        val location = glGetUniformLocation(program!!, name)
        if (location != -1) {
            glUniform1f(location, value)
        }
    }

    fun setUniform(name:String, value: Matrix4f) {
        val location = glGetUniformLocation(program!!, name)
        val buffer: FloatBuffer = BufferUtils.createFloatBuffer(16)
        value.get(buffer)
        if (location != -1) {
            glUniformMatrix4fv(location,false, buffer)
        }
    }

    fun finalize() {
        glDetachShader(program!!, vertexShader!!)
        glDetachShader(program!!, fragmentShader!!)
        glDeleteShader(vertexShader!!)
        glDeleteShader(fragmentShader!!)
        glDeleteProgram(program!!)
    }

    fun bind() {
        glUseProgram(program!!)
    }

    private fun readFile(fileName:String) :String {
        val stringBuilder = StringBuilder()
        try {
            val bufferReader = BufferedReader(FileReader(File(path+"shaders/"+fileName)))
            var hasLine = true
            var line:String?
            while (hasLine){
                line = bufferReader.readLine()
                if (line == null) {
                    hasLine = false
                }else{
                    stringBuilder.append(line)
                    stringBuilder.append("\n")
                }
            }
            bufferReader.close()
        }catch (e:IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}
