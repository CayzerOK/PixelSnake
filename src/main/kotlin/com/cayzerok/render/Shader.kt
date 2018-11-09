package com.cayzerok.render

import org.lwjgl.opengl.GL20.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

private var program:Int? = null
private var vertexShader:Int? = null
private var fragmentShader:Int? = null
class Shader{
    fun init(fileName: String) {
        program = glCreateProgram()

        vertexShader = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader!!, readFile(fileName+".vs"))
        glCompileShader(vertexShader!!)
        if(glGetShaderi(vertexShader!!, GL_COMPILE_STATUS) != 1) {
            throw Exception(glGetShaderInfoLog(vertexShader!!))
        }

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentShader!!, readFile(fileName+".fs"))
        glCompileShader(fragmentShader!!)
        if(glGetShaderi(fragmentShader!!, GL_COMPILE_STATUS) != 1) {
            throw Exception(glGetShaderInfoLog(fragmentShader!!))
        }

        glAttachShader(program!!, vertexShader!!)
        glAttachShader(program!!, fragmentShader!!)

        glBindAttribLocation(program!!,0,"vertices")

        glLinkProgram(program!!)
        if (glGetProgrami(program!!, GL_LINK_STATUS) != 1) {
            throw Exception(glGetProgramInfoLog(program!!))
        }
        glValidateProgram(program!!)

        if (glGetProgrami(program!!, GL_VALIDATE_STATUS) != 1) {
            throw Exception(glGetProgramInfoLog(program!!))
        }
    }

    fun bind() {
        glUseProgram(program!!)
    }

    private fun readFile(fileName:String) :String {
        val stringBuilder = StringBuilder()
        try {
            val bufferReader:BufferedReader = BufferedReader(FileReader(File("./shaders/"+fileName)))
            var hasLine:Boolean = true
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
