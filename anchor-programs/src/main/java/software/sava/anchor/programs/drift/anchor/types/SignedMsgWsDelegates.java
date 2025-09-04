package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

// * Used to store authenticated delegates for swift-like ws connections
public record SignedMsgWsDelegates(PublicKey _address, Discriminator discriminator, PublicKey[] delegates) implements Borsh {

  public static final int DELEGATES_OFFSET = 8;

  public static SignedMsgWsDelegates read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static SignedMsgWsDelegates read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static SignedMsgWsDelegates read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], SignedMsgWsDelegates> FACTORY = SignedMsgWsDelegates::read;

  public static SignedMsgWsDelegates read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var delegates = Borsh.readPublicKeyVector(_data, i);
    return new SignedMsgWsDelegates(_address, discriminator, delegates);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.writeVector(delegates, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.lenVector(delegates);
  }
}
