package xyz.bluspring.snowyowl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.bluspring.snowyowl.duck.ExtendedHungerManager;
import xyz.bluspring.snowyowl.powers.PowerTypes;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;eat(Lnet/minecraft/item/Item;Lnet/minecraft/item/ItemStack;)V"), method = "eatFood")
    public void replaceFood(HungerManager instance, Item item, ItemStack stack, Operation<Void> original) {
        if (PowerTypes.INSTANCE.getNOT_SO_PICKY().isActive((PlayerEntity) (Object) this)) {
            ((ExtendedHungerManager) instance).modifiedEat(item, stack);
        } else {
            original.call(instance, item, stack);
        }
    }
}
