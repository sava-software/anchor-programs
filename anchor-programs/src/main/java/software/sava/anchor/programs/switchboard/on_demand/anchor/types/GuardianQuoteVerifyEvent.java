package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record GuardianQuoteVerifyEvent(PublicKey quote,
                                       PublicKey queue,
                                       PublicKey oracle) implements Borsh {

  public static final int BYTES = 96;

  public static GuardianQuoteVerifyEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var quote = readPubKey(_data, i);
    i += 32;
    final var queue = readPubKey(_data, i);
    i += 32;
    final var oracle = readPubKey(_data, i);
    return new GuardianQuoteVerifyEvent(quote, queue, oracle);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    quote.write(_data, i);
    i += 32;
    queue.write(_data, i);
    i += 32;
    oracle.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
