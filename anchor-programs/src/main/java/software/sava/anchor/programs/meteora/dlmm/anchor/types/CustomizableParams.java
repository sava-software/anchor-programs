package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record CustomizableParams(// Pool price
                                 int activeId,
                                 // Bin step
                                 int binStep,
                                 // Base factor
                                 int baseFactor,
                                 // Activation type. 0 = Slot, 1 = Time. Check ActivationType enum
                                 int activationType,
                                 // Whether the pool has an alpha vault
                                 boolean hasAlphaVault,
                                 // Decide when does the pool start trade. None = Now
                                 OptionalLong activationPoint,
                                 // Pool creator have permission to enable/disable pool with restricted program validation. Only applicable for customizable permissionless pool.
                                 boolean creatorPoolOnOffControl,
                                 // Base fee power factor
                                 int baseFeePowerFactor,
                                 // Padding, for future use
                                 byte[] padding) implements Borsh {

  public static final int PADDING_LEN = 62;
  public static CustomizableParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var activeId = getInt32LE(_data, i);
    i += 4;
    final var binStep = getInt16LE(_data, i);
    i += 2;
    final var baseFactor = getInt16LE(_data, i);
    i += 2;
    final var activationType = _data[i] & 0xFF;
    ++i;
    final var hasAlphaVault = _data[i] == 1;
    ++i;
    final var activationPoint = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (activationPoint.isPresent()) {
      i += 8;
    }
    final var creatorPoolOnOffControl = _data[i] == 1;
    ++i;
    final var baseFeePowerFactor = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[62];
    Borsh.readArray(padding, _data, i);
    return new CustomizableParams(activeId,
                                  binStep,
                                  baseFactor,
                                  activationType,
                                  hasAlphaVault,
                                  activationPoint,
                                  creatorPoolOnOffControl,
                                  baseFeePowerFactor,
                                  padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, activeId);
    i += 4;
    putInt16LE(_data, i, binStep);
    i += 2;
    putInt16LE(_data, i, baseFactor);
    i += 2;
    _data[i] = (byte) activationType;
    ++i;
    _data[i] = (byte) (hasAlphaVault ? 1 : 0);
    ++i;
    i += Borsh.writeOptional(activationPoint, _data, i);
    _data[i] = (byte) (creatorPoolOnOffControl ? 1 : 0);
    ++i;
    _data[i] = (byte) baseFeePowerFactor;
    ++i;
    i += Borsh.writeArrayChecked(padding, 62, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 4
         + 2
         + 2
         + 1
         + 1
         + (activationPoint == null || activationPoint.isEmpty() ? 1 : (1 + 8))
         + 1
         + 1
         + Borsh.lenArray(padding);
  }
}
