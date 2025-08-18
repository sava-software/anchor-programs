package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CreateStrategyParams(PublicKey lender,
                                   long originationCap,
                                   long liquidityBuffer,
                                   long interestFee,
                                   long originationFee,
                                   long principalFee,
                                   boolean originationsEnabled,
                                   ExternalYieldSourceArgs externalYieldSourceArgs) implements Borsh {

  public static CreateStrategyParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lender = readPubKey(_data, i);
    i += 32;
    final var originationCap = getInt64LE(_data, i);
    i += 8;
    final var liquidityBuffer = getInt64LE(_data, i);
    i += 8;
    final var interestFee = getInt64LE(_data, i);
    i += 8;
    final var originationFee = getInt64LE(_data, i);
    i += 8;
    final var principalFee = getInt64LE(_data, i);
    i += 8;
    final var originationsEnabled = _data[i] == 1;
    ++i;
    final var externalYieldSourceArgs = _data[i++] == 0 ? null : ExternalYieldSourceArgs.read(_data, i);
    return new CreateStrategyParams(lender,
                                    originationCap,
                                    liquidityBuffer,
                                    interestFee,
                                    originationFee,
                                    principalFee,
                                    originationsEnabled,
                                    externalYieldSourceArgs);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    lender.write(_data, i);
    i += 32;
    putInt64LE(_data, i, originationCap);
    i += 8;
    putInt64LE(_data, i, liquidityBuffer);
    i += 8;
    putInt64LE(_data, i, interestFee);
    i += 8;
    putInt64LE(_data, i, originationFee);
    i += 8;
    putInt64LE(_data, i, principalFee);
    i += 8;
    _data[i] = (byte) (originationsEnabled ? 1 : 0);
    ++i;
    i += Borsh.writeOptional(externalYieldSourceArgs, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 8
         + 8
         + 8
         + 8
         + 8
         + 1
         + (externalYieldSourceArgs == null ? 1 : (1 + Borsh.len(externalYieldSourceArgs)));
  }
}
