package software.sava.anchor.programs.marinade.anchor.types;

import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record ConfigLpParams(Fee minFee,
                             Fee maxFee,
                             OptionalLong liquidityTarget,
                             Fee treasuryCut) implements Borsh {

  public static ConfigLpParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var minFee = _data[i++] == 0 ? null : Fee.read(_data, i);
    if (minFee != null) {
      i += Borsh.len(minFee);
    }
    final var maxFee = _data[i++] == 0 ? null : Fee.read(_data, i);
    if (maxFee != null) {
      i += Borsh.len(maxFee);
    }
    final var liquidityTarget = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (liquidityTarget.isPresent()) {
      i += 8;
    }
    final var treasuryCut = _data[i++] == 0 ? null : Fee.read(_data, i);
    return new ConfigLpParams(minFee,
                              maxFee,
                              liquidityTarget,
                              treasuryCut);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(minFee, _data, i);
    i += Borsh.writeOptional(maxFee, _data, i);
    i += Borsh.writeOptional(liquidityTarget, _data, i);
    i += Borsh.writeOptional(treasuryCut, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenOptional(minFee) + Borsh.lenOptional(maxFee) + 9 + Borsh.lenOptional(treasuryCut);
  }
}
