package software.sava.anchor.programs.jupiter.limit.anchor.types;

import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CreateOrderEvent(PublicKey orderKey,
                               PublicKey maker,
                               PublicKey inputMint,
                               PublicKey outputMint,
                               long inAmount,
                               long outAmount,
                               OptionalLong expiredAt) implements Borsh {

  public static CreateOrderEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var orderKey = readPubKey(_data, i);
    i += 32;
    final var maker = readPubKey(_data, i);
    i += 32;
    final var inputMint = readPubKey(_data, i);
    i += 32;
    final var outputMint = readPubKey(_data, i);
    i += 32;
    final var inAmount = getInt64LE(_data, i);
    i += 8;
    final var outAmount = getInt64LE(_data, i);
    i += 8;
    final var expiredAt = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    return new CreateOrderEvent(orderKey,
                                maker,
                                inputMint,
                                outputMint,
                                inAmount,
                                outAmount,
                                expiredAt);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    orderKey.write(_data, i);
    i += 32;
    maker.write(_data, i);
    i += 32;
    inputMint.write(_data, i);
    i += 32;
    outputMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, outAmount);
    i += 8;
    i += Borsh.writeOptional(expiredAt, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 32
         + 32
         + 32
         + 8
         + 8
         + 9;
  }
}
