package ch.leadrian.samp.cidl.visitor

import ch.leadrian.samp.cidl.CIDLBaseVisitor
import ch.leadrian.samp.cidl.CIDLParser
import ch.leadrian.samp.cidl.model.Constant
import ch.leadrian.samp.cidl.model.Declarations
import ch.leadrian.samp.cidl.model.Function

class DeclarationsVisitor(
        private val constantDeclarationVisitor: ConstantDeclarationVisitor,
        private val functionDeclarationVisitor: FunctionDeclarationVisitor
) : CIDLBaseVisitor<Declarations>() {

    override fun visitDeclarations(ctx: CIDLParser.DeclarationsContext): Declarations {
        val constants: List<Constant> = visitContantDeclarations(ctx)
        val functions: List<Function> = visitFunctionDeclarations(ctx)
        return Declarations(
                constants = constants,
                functions = functions
        )
    }

    private fun visitFunctionDeclarations(ctx: CIDLParser.DeclarationsContext): List<Function> =
            ctx.declaration()
                    .asSequence()
                    .mapNotNull { it.functionDeclaration() }
                    .map { functionDeclarationVisitor.visit(it) }
                    .toList()

    private fun visitContantDeclarations(ctx: CIDLParser.DeclarationsContext): List<Constant> =
            ctx
                    .declaration()
                    .asSequence()
                    .mapNotNull { it.constantDeclaration() }
                    .map { constantDeclarationVisitor.visit(it) }
                    .toList()

}