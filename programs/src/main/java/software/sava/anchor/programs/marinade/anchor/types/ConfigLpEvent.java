package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ConfigLpEvent(PublicKey state,
                            FeeValueChange minFeeChange,
                            FeeValueChange maxFeeChange,
                            U64ValueChange liquidityTargetChange,
                            FeeValueChange treasuryCutChange) implements Borsh {


  public static ConfigLpEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var minFeeChange = _data[i++] == 0 ? null : FeeValueChange.read(_data, i);
    if (minFeeChange != null) {
      i += Borsh.len(minFeeChange);
    }
    final var maxFeeChange = _data[i++] == 0 ? null : FeeValueChange.read(_data, i);
    if (maxFeeChange != null) {
      i += Borsh.len(maxFeeChange);
    }
    final var liquidityTargetChange = _data[i++] == 0 ? null : U64ValueChange.read(_data, i);
    if (liquidityTargetChange != null) {
      i += Borsh.len(liquidityTargetChange);
    }
    final var treasuryCutChange = _data[i++] == 0 ? null : FeeValueChange.read(_data, i);
    return new ConfigLpEvent(state,
                             minFeeChange,
                             maxFeeChange,
                             liquidityTargetChange,
                             treasuryCutChange);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    i += Borsh.writeOptional(minFeeChange, _data, i);
    i += Borsh.writeOptional(maxFeeChange, _data, i);
    i += Borsh.writeOptional(liquidityTargetChange, _data, i);
    i += Borsh.writeOptional(treasuryCutChange, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + Borsh.lenOptional(minFeeChange)
         + Borsh.lenOptional(maxFeeChange)
         + Borsh.lenOptional(liquidityTargetChange)
         + Borsh.lenOptional(treasuryCutChange);
  }
}