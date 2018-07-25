package ch.leadrian.samp.cidl.visitor

import ch.leadrian.samp.cidl.CIDLBaseVisitor
import ch.leadrian.samp.cidl.CIDLParser
import ch.leadrian.samp.cidl.model.Function

class FunctionDeclarationVisitor(
        private val parameterVisitor: ParameterVisitor,
        private val attributeVisitor: AttributeVisitor
) : CIDLBaseVisitor<Function>() {

    override fun visitFunctionDeclaration(ctx: CIDLParser.FunctionDeclarationContext): Function {
        val parameters = visitParameters(ctx)
        val attributes = visitAttributes(ctx)
        return Function(
                type = ctx.typeName().IDENT().text,
                name = ctx.functionName().IDENT().text,
                parameters = parameters,
                attributes = attributes
        )
    }

    private fun visitAttributes(ctx: CIDLParser.FunctionDeclarationContext) =
            ctx
                    .attributes()
                    ?.attributeList()
                    ?.attribute()
                    ?.map { attributeVisitor.visit(it) }
                    .orEmpty()

    private fun visitParameters(ctx: CIDLParser.FunctionDeclarationContext) =
            ctx
                    .parameters()
                    ?.parameterList()
                    ?.parameter()
                    ?.map { parameterVisitor.visit(it) }
                    .orEmpty()
}