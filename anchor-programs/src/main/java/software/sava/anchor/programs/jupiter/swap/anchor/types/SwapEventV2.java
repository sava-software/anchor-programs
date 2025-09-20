package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapEventV2(PublicKey inputMint,
                          long inputAmount,
                          PublicKey outputMint,
                          long outputAmount) implements Borsh {

  public static final int BYTES = 80;

  public static SwapEventV2 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var inputMint = readPubKey(_data, i);
    i += 32;
    final var inputAmount = getInt64LE(_data, i);
    i += 8;
    final var outputMint = readPubKey(_data, i);
    i += 32;
    final var outputAmount = getInt64LE(_data, i);
    return new SwapEventV2(inputMint,
                           inputAmount,
                           outputMint,
                           outputAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    inputMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, inputAmount);
    i += 8;
    outputMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, outputAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
