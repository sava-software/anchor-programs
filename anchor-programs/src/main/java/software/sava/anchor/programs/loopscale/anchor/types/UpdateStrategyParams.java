package software.sava.anchor.programs.loopscale.anchor.types;

import java.lang.Boolean;

import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record UpdateStrategyParams(Boolean originationsEnabled,
                                   OptionalLong liquidityBuffer,
                                   OptionalLong interestFee,
                                   OptionalLong originationFee,
                                   OptionalLong principalFee,
                                   OptionalLong originationCap,
                                   PublicKey marketInformation,
                                   ExternalYieldSourceArgs externalYieldSourceArgs) implements Borsh {

  public static UpdateStrategyParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var originationsEnabled = _data[i++] == 0 ? null : _data[i] == 1;
    if (originationsEnabled != null) {
      ++i;
    }
    final var liquidityBuffer = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (liquidityBuffer.isPresent()) {
      i += 8;
    }
    final var interestFee = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (interestFee.isPresent()) {
      i += 8;
    }
    final var originationFee = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (originationFee.isPresent()) {
      i += 8;
    }
    final var principalFee = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (principalFee.isPresent()) {
      i += 8;
    }
    final var originationCap = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (originationCap.isPresent()) {
      i += 8;
    }
    final var marketInformation = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (marketInformation != null) {
      i += 32;
    }
    final var externalYieldSourceArgs = _data[i++] == 0 ? null : ExternalYieldSourceArgs.read(_data, i);
    return new UpdateStrategyParams(originationsEnabled,
                                    liquidityBuffer,
                                    interestFee,
                                    originationFee,
                                    principalFee,
                                    originationCap,
                                    marketInformation,
                                    externalYieldSourceArgs);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(originationsEnabled, _data, i);
    i += Borsh.writeOptional(liquidityBuffer, _data, i);
    i += Borsh.writeOptional(interestFee, _data, i);
    i += Borsh.writeOptional(originationFee, _data, i);
    i += Borsh.writeOptional(principalFee, _data, i);
    i += Borsh.writeOptional(originationCap, _data, i);
    i += Borsh.writeOptional(marketInformation, _data, i);
    i += Borsh.writeOptional(externalYieldSourceArgs, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (originationsEnabled == null ? 1 : (1 + 1))
         + (liquidityBuffer == null || liquidityBuffer.isEmpty() ? 1 : (1 + 8))
         + (interestFee == null || interestFee.isEmpty() ? 1 : (1 + 8))
         + (originationFee == null || originationFee.isEmpty() ? 1 : (1 + 8))
         + (principalFee == null || principalFee.isEmpty() ? 1 : (1 + 8))
         + (originationCap == null || originationCap.isEmpty() ? 1 : (1 + 8))
         + (marketInformation == null ? 1 : (1 + 32))
         + (externalYieldSourceArgs == null ? 1 : (1 + Borsh.len(externalYieldSourceArgs)));
  }
}
