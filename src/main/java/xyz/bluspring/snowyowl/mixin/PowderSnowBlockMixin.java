package xyz.bluspring.axolotlorigin.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.bluspring.axolotlorigin.powers.AxolotlPowerTypes;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @Inject(at = @At("HEAD"), method = "canWalkOnPowderSnow", cancellable = true)
    private static void allowWalkingOnPowderedSnow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (AxolotlPowerTypes.INSTANCE.getAntiFreeze().isActive(entity)) {
            cir.setReturnValue(true);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setInPowderSnow(Z)V"), method = "onEntityCollision")
    public void preventFreeze(Entity instance, boolean inPowderSnow) {
        if (!AxolotlPowerTypes.INSTANCE.getAntiFreeze().isActive(instance)) {
            instance.setInPowderSnow(inPowderSnow);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V"), method = "onEntityCollision")
    public void noFreezeSlowdown(Entity instance, BlockState state, Vec3d multiplier) {
        if (!AxolotlPowerTypes.INSTANCE.getAntiFreeze().isActive(instance)) {
            instance.slowMovement(state, multiplier);
        }
    }
}
