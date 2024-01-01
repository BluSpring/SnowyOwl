package xyz.bluspring.snowyowl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.bluspring.snowyowl.powers.PowerTypes;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @Inject(at = @At("HEAD"), method = "canWalkOnPowderSnow", cancellable = true)
    private static void allowWalkingOnPowderedSnow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (PowerTypes.INSTANCE.getSNOW_SHOES().isActive(entity)) {
            cir.setReturnValue(true);
        }
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setInPowderSnow(Z)V"), method = "onEntityCollision")
    public void preventFreeze(Entity instance, boolean inPowderSnow, Operation<Void> original) {
        if (!PowerTypes.INSTANCE.getINSULATION().isActive(instance)) {
            original.call(instance, inPowderSnow);
        }
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V"), method = "onEntityCollision")
    public void noFreezeSlowdown(Entity instance, BlockState state, Vec3d multiplier, Operation<Void> original) {
        if (!PowerTypes.INSTANCE.getINSULATION().isActive(instance)) {
            original.call(instance, state, multiplier);
        }
    }
}
