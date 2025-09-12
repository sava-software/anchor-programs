package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

public record EditionMarkerV2(PublicKey _address, Key key, byte[] ledger) implements Borsh {

  public static final int KEY_OFFSET = 0;
  public static final int LEDGER_OFFSET = 1;

  public static Filter createKeyFilter(final Key key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key.write());
  }

  public static EditionMarkerV2 read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static EditionMarkerV2 read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static EditionMarkerV2 read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], EditionMarkerV2> FACTORY = EditionMarkerV2::read;

  public static EditionMarkerV2 read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = Key.read(_data, i);
    i += Borsh.len(key);
    final var ledger = Borsh.readbyteVector(_data, i);
    return new EditionMarkerV2(_address, key, ledger);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(key, _data, i);
    i += Borsh.writeVector(ledger, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(key) + Borsh.lenVector(ledger);
  }
}
