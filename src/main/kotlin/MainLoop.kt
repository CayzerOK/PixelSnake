import org.lwjgl.opengl.GL11.*
fun mainLoop() {
    glClear(GL_COLOR_BUFFER_BIT)
    glBegin(GL_QUADS)
    glTexCoord2f(-1f,-1f)
    glVertex2f(-1f,-1f)

    glTexCoord2f(1f,-1f)
    glVertex2f(1f,-1f)

    glTexCoord2f(1f,1f)
    glVertex2f(1f,1f)

    glTexCoord2f(-1f,1f)
    glVertex2f(-1f,1f)
    glEnd()
}