package software.sava.anchor.programs.glam.mint.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record RequestQueue(PublicKey _address,
                           Discriminator discriminator,
                           PublicKey glamState,
                           PublicKey glamMint,
                           PendingRequest[] data) implements Borsh {

  public static final Discriminator DISCRIMINATOR = toDiscriminator(172, 124, 172, 253, 233, 63, 70, 234);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int GLAM_STATE_OFFSET = 8;
  public static final int GLAM_MINT_OFFSET = 40;
  public static final int DATA_OFFSET = 72;

  public static Filter createGlamStateFilter(final PublicKey glamState) {
    return Filter.createMemCompFilter(GLAM_STATE_OFFSET, glamState);
  }

  public static Filter createGlamMintFilter(final PublicKey glamMint) {
    return Filter.createMemCompFilter(GLAM_MINT_OFFSET, glamMint);
  }

  public static RequestQueue read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static RequestQueue read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static RequestQueue read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], RequestQueue> FACTORY = RequestQueue::read;

  public static RequestQueue read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var glamState = readPubKey(_data, i);
    i += 32;
    final var glamMint = readPubKey(_data, i);
    i += 32;
    final var data = Borsh.readVector(PendingRequest.class, PendingRequest::read, _data, i);
    return new RequestQueue(_address, discriminator, glamState, glamMint, data);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    glamState.write(_data, i);
    i += 32;
    glamMint.write(_data, i);
    i += 32;
    i += Borsh.writeVector(data, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32 + 32 + Borsh.lenVector(data);
  }
}
